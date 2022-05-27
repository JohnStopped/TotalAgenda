import json
import psycopg2 as psql

def lambda_handler(event, context):
    
    '''
    json format of body for deleteEvent
    {
        "event_id": int
    }
    '''
    complete_req = event
    
    body = json.loads(event['body'])
    
    response = {
        "state": 0,
        "desc": "Bad use of parameters",
        "event_id": 0
    }
    
    #If format isn't correct function will not con
    if ('event_id' in body and len(list(body))==1):
        #Connect to database
        conn = psql.connect(dbname="BaseDatosSDSW", user="functionsuser", password="ferrariYaFallo", host="bbdd-totalagenda.c0fmdhhpll94.eu-west-3.rds.amazonaws.com", port="54321")
        cur = conn.cursor()
        
        cur.execute("select event_id from events_use where event_id=%s",(body['event_id'],))
        ddbb_response = cur.fetchone()
        
        print (ddbb_response)
        
        if (ddbb_response != None):
            cur.execute("delete from events_use where event_id=%s",(body['event_id'],))
            response = {
                "state": 1,
                "desc": "Event selected has been deleted",
                "event_id": body['event_id']
            }
        else:
            print ("Event-id sent doesn't exit")
            response = {
                "state": 0,
                "desc": "Event-id sent doesn't exit",
                "event_id": 0
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

