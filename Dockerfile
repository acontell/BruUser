FROM airhacks/glassfish
COPY ./target/bruuser.war ${DEPLOYMENT_DIR}
