# Ab2d-Events

The event service listens and extracts events from the Amazon SQS, then it processes and logs those events.

Processing:
The events service will process the events(ex. if a job failed) and send a notification to slack channel to notify the team member.

Logging:
The events service will log the events(ex. error or status message) into the database for future tracking.

### Running Locally with Intelij

1. Run postgress and localstack locally using Docker

   ```ShellSession
   $ docker-compose up db localstack
   ```

Event Service Setup
1. Select Run/Debug Configuration > Edit Configurations > add configuration (+) > Spring Boot
2. In Main Class select gov.cms.ab2d.eventlogger.SpringBootApp
3. Go to 1Password and search for 'Event Service Local Env Variables'. Use the configs in the note for the Environment Variables field
4. Bring up AB2D and then run the configuration

### Add new messages to be processed
1. Follow instructions in the [Events Client](https://github.com/CMSgov/AB2D-Libs/tree/main/ab2d-events-client) to add new messages (events)
2. Go to file gov/cms/ab2d/eventlogger/api/EventsListener.java and add your new message. 
```aidl
            case "NewMessage" -> {
                //Send message to logManager. Most likly the logging type you needs exist already.
                //if not you'll have to add new logger to event service and methods to LogManager
                //Look at past logging (sql, kinesis and slack)
                logManager.methodcall(((MyMessage) sqsMessage).getVariablesFromObject());
            }
```





