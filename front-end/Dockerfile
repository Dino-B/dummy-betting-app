FROM node:14.5.0

WORKDIR /app

COPY front-end/package*.json ./

RUN npm config rm proxy
RUN npm config rm https-proxy

RUN npm install
RUN npm install axios vue-router vuex

COPY front-end/src src/
COPY front-end/public public/

RUN npm run build
