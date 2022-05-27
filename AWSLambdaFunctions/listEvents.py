import json
import psycopg2 as psql
import random
from datetime import datetime
from datetime import timedelta

def lambda_handler(event, context):
    '''
    json format of body for listEvents
    {
        "session_id": int
    }
    '''
    complete_req = event
    
    body = json.loads(event['body']) #volver a poner con body
    
    response = {
        "state": 0,
        "desc": "Bad use of parameters",
        "list_events": []
    }
    
    #If format isn't correct function will not continue
    if ('session_id' in body and len(list(body))==1):
        conn = psql.connect(dbname="BaseDatosSDSW", user="functionsuser", password="ferrariYaFallo", host="bbdd-totalagenda.c0fmdhhpll94.eu-west-3.rds.amazonaws.com", port="54321 ")
        cur = conn.cursor()
        cur.execute("SELECT session_id FROM users where session_id=%s;",(body['session_id'],))
        session_id = cur.fetchone()
        
        if (session_id != None):
            cur.execute("SELECT email FROM users where session_id=%s;",(session_id[0],))
            email = cur.fetchone()       
            
            if (email != None):   
                cur.execute("select event_id,date1,advice_date,name,color,note from events_use where email=%s order by date1 asc;",(email[0],))
                list_events = []
                event_json = {}
                
                ddbb_response = cur.fetchall()
                
                for row in ddbb_response:
                    event_id = row[0]
                    event_date = row[1]
                    event_date_ddbb = event_date.strftime('%Y-%m-%d %H:%M:%S')
                    event_advice_date = row[2]
                    
                    if (event_advice_date != None): #Checks if advice_date is empty, if it were, it could raise an exception
                        event_advice_date_ddbb = event_advice_date.strftime('%Y-%m-%d %H:%M:%S')
                    else:
                        event_advice_date_ddbb = event_advice_date
                        
                    event_name = row[3]
                    event_color = row[4]
                    event_note = row[5]
                    
                    event_json = {
                        "event_id": event_id,
                        "date": event_date_ddbb,
                        "advice_date": event_advice_date_ddbb,
                        "name": event_name,
                        "color": event_color,
                        "note": event_note
                    }
                    
                    list_events.append(event_json)
                
                response = {
                    "state": 1,
                    "desc": "List of events recovered successfully",
                    "list_events": list_events
                }
                
            else:
                print ("Session_id doesn't fit with any email")
                response = {
                    "state": 0,
                    "desc": "Session_id doesn't fit with any email",
                    "list_events": []
                }
        else:
            print ("Session_id sent doesn't exit")
            response = {
                "state": 0,
                "desc": "Session_id sent doesn't exit",
                "list_events": []
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
