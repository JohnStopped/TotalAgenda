import json
import psycopg2 as psql

def lambda_handler(event, context):
    
    complete_req = event
    body = json.loads(event['body'])
    
    response = {
        "state": 0,
        "desc": "Bad use of parameters"
    }
    
    #If format isn't correct function will not continue
    if ('email' in body and 'passwd' in body and 'old_passwd' and len(list(body))==3):
        
        if (body['passwd'] != body['old_passwd']):
            conn = psql.connect(dbname="BaseDatosSDSW", user="functionsuser", password="ferrariYaFallo", host="bbdd-totalagenda.c0fmdhhpll94.eu-west-3.rds.amazonaws.com", port="54321 ")
            cur = conn.cursor()
            cur.execute("SELECT * FROM users where email=%s;",(body['email'],))
            user = cur.fetchone()
            
            if (user != None): #This email exits then function could modify the user
                cur.execute("SELECT * FROM users where email=%s and passwd=%s;",(body['email'],body['old_passwd'],)) #It checks if the old passwd were correct
                row = cur.fetchone()
                if (row != None):
                    cur.execute("update users set passwd=%s where email=%s", (body['passwd'],body['email'],)) #insert new passwd
                    
                    response = {
                        "state": 1,
                        "desc": "Password was updated successfully"
                    }
                    
                else:
                    print ("Old passwd sent is not correct")
                    response = {
                        "state": 0,
                        "desc": "Old passwd sent is not correct"
                    }
            else:
                print ("Email sent doesn't exit")
                response = {
                    "state": 0,
                    "desc": "Email sent doesn't exit"
                }
                
            conn.commit()
            cur.close()
            conn.close()
            
        else:
            print ("Old and new passwd are equal")
            response = {
                "state": 0,
                "desc": "Old and new passwd are equal"
            }
                
    return {
        "isBase64Encoded": False,
        "statusCode": 200,
        "headers": {
            "Content-Type": "application/json"
        },
        "body": json.dumps(response)
    }
