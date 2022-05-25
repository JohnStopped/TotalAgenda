import json
import psycopg2 as psql
import random
from datetime import datetime
from datetime import timedelta

def lambda_handler(event, context):
    '''
    json format of body for createEvent
    {
        "session_id": int,
        "date": "2004-10-19 10:23:54",
        "name": string,
        "advice_date": "2004-10-19 10:23:54", (optional)
        "color": string, (optional)
        "note": string (optional) (255 max)
    }
    '''
    complete_req = event
    
    body = json.loads(event['body']) #volver a poner con body
    
    response = {
        "state": 0,
        "desc": "Bad use of parameters",
        "session-id": 0
    }
    
    #If format isn't correct function will not continue
    if ('session_id' in body and 'date' in body and 'name' in body and len(list(body))>=3):
        conn = psql.connect(dbname="BaseDatosSDSW", user="functionsuser", password="ferrariYaFallo", host="bbdd-totalagenda.c0fmdhhpll94.eu-west-3.rds.amazonaws.com", port="54321 ")
        cur = conn.cursor()
        cur.execute("SELECT session_id FROM users where session_id=%s;",(body['session_id'],))
        session_id = cur.fetchone()[0]
        
        if (session_id != None):
            cur.execute("SELECT email FROM users where session_id=%s;",(session_id,))
            email = cur.fetchone()[0]        
            
            event_id = random.randint(0,65535)
            # check this event_id doesn't exit in DDBB
            
            cur.execute("select event_id from events_use where event_id=%s;",(event_id,))
            ddbb_event_id = cur.fetchone()
            while (ddbb_event_id!=None):
                event_id = random.randint(0,65535)
                cur.execute("select event_id from events_use where event_id=%s;",(event_id,))
                ddbb_event_id = cur.fetchone()
            
            # Set values to insert
            event_date = datetime.strptime(body['date'],'%Y-%m-%d %H:%M:%S')
            day = timedelta (days=1) # it represents a day to set default advice_date
            advice_date = event_date - day # 1 day before
            name = body['name']
            color = 'white'
            note = ''
            
            if ('advice_date' in body): #if advice_date is present in the msg, use it
                advice_date = datetime.strptime(body['advice_date'],'%Y-%m-%d %H:%M:%S')
            
            if ('color' in body): #if color is present in the msg, use it
                color = body['color']    
                
            if ('note' in body): #if note exists in the msg, use it
                note = body['note']    
                
            # check for exactly the same data
            cur.execute("select * from events_use where email=%s and date1=%s and advice_date=%s and name=%s;", (email,body['date'],advice_date.strftime('%Y-%m-%d %H:%M:%S'),name,))
            if (cur.fetchone() == None): 
                # insert required values
                cur.execute ("insert into events_use (event_id, email, date1, advice_date, name, color, note) values (%s,%s,%s,%s,%s,%s,%s)", (event_id,email,body['date'],advice_date.strftime('%Y-%m-%d %H:%M:%S'),name,color,note,))
                
                response = {
                    "state": 1,
                    "desc": "Event created succesfully",
                    "session-id": session_id
                }
                
            else:    
                response = {
                    "state": 0,
                    "desc": "Most important data is in a created event",
                    "session-id": session_id
                }
        else:
            print ("Session_id sent doesn't exit")
            response = {
                "state": 0,
                "desc": "Session_id sent doesn't exit",
                "session-id": 0
            }
            
        conn.commit()
        cur.close()
        conn.close()     
    
    return {
        "isBase64Encoded": False,
        "statusCode": 200,
        "headers": {
            "Content-Type": "application/json"
        },
        "body": json.dumps(response)
    }
