gcloud auth activate-service-account ${env.ACCOUNT_EMAIL} --key-file ${COMPUTE_CREDENTIALS}
gcloud auth configure-docker gcr.io -q
docker build --build-arg ARTIFACT_ID,ARTIFACT_VERSION,APPLICATION_PORT . -t ${ARTIFACTID}:${VERSION}
docker images
docker tag ${ARTIFACTID}:${VERSION} gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}
docker push gcr.io/${env.PROJECT_ID}/${ARTIFACTID}:${VERSION}