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
    
    #If format isn't correct function will not continue
    if ('email' in event and 'passwd' in event and len(list(event))==2):
        conn = psql.connect(dbname="BaseDatosSDSW", user="functionsuser", password="ferrariYaFallo", host="bbdd-totalagenda.c0fmdhhpll94.eu-west-3.rds.amazonaws.com", port="54321 ")
        cur = conn.cursor()
        cur.execute("SELECT * FROM users where email=%s;",(event['email'],))
        user = cur.fetchone()
        
        if (user == None): #This email doesn't exit then function could create the user
            email = event['email']
            passwd = event['passwd']
            
            cur.execute("insert into users (email, passwd, session_id) values (%s,%s,%s)", (email,passwd,0,))
            
            response = {
                "state": 1,
                "desc": "User created succesfully",
                "session-id": 0
            }
            
        else:
            print ("Email sent exits already")
            response = {
                "state": 0,
                "desc": "Email sent exits already",
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
