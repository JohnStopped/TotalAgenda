import json
import psycopg2 as psql
from datetime import datetime
import smtplib
import ssl
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from datetime import timedelta

def lambda_handler(event, context):
    
    complete_req = event
    print (complete_req) #DEBUG
    
    #If format isn't correct function will not con
    if ('rule' in event and 'period' in event and len(list(event))==2):
        now = datetime.now() #Recover time where the function is triggered
        twoHours = timedelta (hours=2)
        now = now + twoHours
        ddbb_now = now.strftime('%Y-%m-%d %H:%M:%S') # Convertion to DDBB format
        
        print('Now time: ' + ddbb_now) #DEBUG
        
        #Connect to database
        conn = psql.connect(dbname="BaseDatosSDSW", user="functionsuser", password="ferrariYaFallo", host="bbdd-totalagenda.c0fmdhhpll94.eu-west-3.rds.amazonaws.com", port="54321 ")
        cur = conn.cursor()

        # Recover nexts scheduled events not adviced
        cur.execute("select event_id,email,date1,advice_date,name from events_use where date1>%s and advice_date is not null", (ddbb_now,))
        bbdd_response = cur.fetchall() #Contains list of tuples (event_id,email,date1,advice_date,name)
        
        sender = 'totalagendaadvisorevents@gmail.com'
        password = 'nxdvuzqajoyxvpgk'
        
        if (bbdd_response != []): #There are some events to advice
            for row in bbdd_response:
                event_id = row[0]
                email = row[1]
                date1 = row[2]
                advice_date = row[3]
                event_name = row[4]
                #advice_date = datetime.strptime(ddbb_advice_date,'%Y-%m-%d %H:%M:%S') #Not needed
                
                if (now >= advice_date): #The function only notificates past (or same) not notificated advices dates
                    email_body = '''Dear user.
                    This email has been sent to you to remind you that you have an event scheduled in the near future.
                    Event info:
                    \tName: {}
                    \tEvent date: {}
                    '''.format(event_name,date1)
                    
                    message = MIMEMultipart()
                    message['From'] = sender
                    message['To'] = email
                    message['Subject'] = 'Auto-email for remember an event'
                    message.attach(MIMEText(email_body, 'plain'))
                    
                    context = ssl.create_default_context()
                    
                    session = smtplib.SMTP_SSL('smtp.gmail.com', 465, context=context) #use gmail with port
                    session.login(sender, password) #login with mail_id and password
                    text = message.as_string()
                    session.sendmail(sender, email, text)
                    session.quit()
                    print('Mail Sent')
                    
                    # Delete advice date of this event on database to not advice more
                    cur.execute("update events_use set advice_date=%s where event_id=%s", (None,event_id,))

        else:
            print ('No events to advice')
            
        conn.commit()
        cur.close()
        conn.close()       
