FROM node:12
WORKDIR /home
RUN apt-get update && apt-get install -y \
    git \
    apache2



# Get all the component
COPY ./git_cmd.sh /root/git_cmd.sh
RUN chmod +x /root/git_cmd.sh
COPY ./git_cmd_5gr.sh /root/git_cmd_5gr.sh
RUN chmod +x /root/git_cmd_5gr.sh


RUN /root/git_cmd_5gr.sh 5gr-vs /home/slicer
WORKDIR /usr/src/app/app-ui
RUN cp -r /home/slicer/angular-slicer/slicer/. .

COPY ./environment/environments.ts /usr/src/app/app-ui/src/app/environments
RUN npm install
RUN npm install -g @angular/cli
RUN ng build --commonChunk=true --namedChunks=true --optimization=true --sourceMap=false
RUN cp -r dist/* /var/www/html

EXPOSE 80

CMD apachectl -D FOREGROUND


