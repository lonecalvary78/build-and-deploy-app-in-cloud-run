# Build and deploy the Book application (in Sprig Boot) in Cloud Run

## Build and deploy the Book application
To build the application, please run the command below.

That command will submit the new build request to Cloud Build for performing:
 - Build the application in to the container image and push into the container registry (Google Cloud Container Registry)
   
   `gcloud builds submit --config=book-app-build`
   

 - Deploy the application into the Cloud Run using the container image which build on the first step.

 ## Need to be configured
 There is something need to be updated before you proceed to build appliaction.
 
 #### book-app-build.yml
 - Replace {YOUR_COMPUTE_REGION} with the GCP region.