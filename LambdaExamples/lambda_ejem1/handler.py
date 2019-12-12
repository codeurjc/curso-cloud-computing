import boto3
import json

print('Loading function')
dynamodb = boto3.resource('dynamodb')
s3Events = dynamodb.Table('s3-events')


def respond(err, res=None):
    return {
        'statusCode': '400' if err else '200',
        'body': err.message if err else json.dumps(res['Items']),
        'headers': {
            'Content-Type': 'application/json',
        },
    }


def lambda_handler(event, context):

    print("Received event: " + json.dumps(event, indent=2))
    
    if 'Records' in event:
        # Event triggered from S3 (Create or Delete an object)
        for record in event['Records']:
            s3Events.put_item(
                Item={
                        'id': context.aws_request_id, 
                        'type': record['eventName'],
                        'object_name': record['s3']['object']['key'],
                        'author': record['userIdentity']['principalId'],
                        'date': record['eventTime']
                }
            )
            
    else:
        # GET Request, return all events stored in database
        if event['httpMethod'] == "GET":
            return respond(None, s3Events.scan())
        else:
            return respond(ValueError('Unsupported method "{}"'.format(operation)))