import json
import psycopg2 as psql
import random

def lambda_handler(event, context):
    '''
    print (event)
    print (len(list(event)))
    print ('email' in event)
    print ('passwd' in event)
    '''
    complete_req = event
    
    body = json.loads(event['body'])
    #print (body)
    #print(body['email'])
    #print(body['passwd'])
    #print ('El diccionario body tiene ' + str(len(list(body))) + ' elementos.')
    response = {
        "state": 0,
        "desc": "Bad use of parameters",
        "session-id": 0
    }
    
    #If format isn't correct function will not con
    if ('email' in body and 'passwd' in body and len(list(body))==2):
        conn = psql.connect(dbname="BaseDatosSDSW", user="functionsuser", password="ferrariYaFallo", host="bbdd-totalagenda.c0fmdhhpll94.eu-west-3.rds.amazonaws.com", port="54321 ")
        cur = conn.cursor()
        cur.execute("SELECT * FROM users where email=%s;",(body['email'],))
        user = cur.fetchone()
        
        if (user != None):
            email = user[0]
            passwd = user[1]
            
            if (passwd == body['passwd']):
                session_id = user[2]
                
                if (session_id == 0):
                    #session not initializated, error
                    
                    response = {
                       "state": 0,
                       "desc": "Session is not initializated",
                       "session-id": 0
                    }
                else:
                    #session was initializated
                    print ("Session was initializated, closing")
                    
                    cur.execute("update users set session_id=%s where email=%s",(str(0),body['email'],))
                    
                    response = {
                       "state": 1,
                       "desc": "Session was initializated and now it is closed, sending old session_id",
                       "session-id": session_id
                    }
            else:
                print ("Password sent is wrong")
                response = {
                    "state": 0,
                    "desc": "Password sent is wrong",
                    "session-id": 0
                }
               
        else:
            print ("Email sent doesn't exit")
            response = {
                "state": 0,
                "desc": "Email sent doesn't exit",
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
