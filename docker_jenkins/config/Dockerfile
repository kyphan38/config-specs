FROM jenkins/jenkins:2.460-jdk17

USER root

# Update and install some necessary packages
RUN apt-get update -y && \
  apt-get install -y --no-install-recommends curl && \
  apt-get clean && \
  rm -rf /var/lib/apt/list/* /tmp/* /var/tmp/*

# Install plugins
# RUN jenkins-plugin-cli --plugins pipeline-utility-steps:2.16.2

# Set working directory
WORKDIR /var/jenkins_home