FROM airhacks/wildfly
COPY ./target/bruuser.war ${DEPLOYMENT_DIR}
