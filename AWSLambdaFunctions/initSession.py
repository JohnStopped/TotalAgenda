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
    
    response = {
        "state": 0,
        "desc": "Bad use of parameters",
        "session-id": 0
    }
    
    #If format isn't correct function will not con
    if ('email' in event and 'passwd' in event and len(list(event))==2):
        conn = psql.connect(dbname="BaseDatosSDSW", user="functionsuser", password="ferrariYaFallo", host="bbdd-totalagenda.c0fmdhhpll94.eu-west-3.rds.amazonaws.com", port="54321 ")
        cur = conn.cursor()
        cur.execute("SELECT * FROM users where email=%s;",(event['email'],))
        user = cur.fetchone()
        
        if (user != None):
            email = user[0]
            passwd = user[1]
            
            if (passwd == event['passwd']):
                session_id = user[2]
                
                if (session_id == 0):
                    #session not initializated, generate session-id
                    session_id = random.randint(1,255)
                    
                    #update database
                    cur.execute("update users set session_id=%s where email=%s",(str(session_id),event['email'],))
                    
                    response = {
                       "state": 1,
                       "desc": "Connection to database correct",
                       "session-id": session_id
                    }
                else:
                    #session was initializated
                    print ("Session already was initializated, only one session at same time")
                    response = {
                       "state": 0,
                       "desc": "Session already was initializated",
                       "session-id": 0
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
        "body": str(response)
    }
