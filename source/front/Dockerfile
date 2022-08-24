FROM node:18 as builder
WORKDIR ./
COPY ./package*.json ./
RUN npm install # npm install
COPY . .
RUN npm run build # 빌드


FROM nginx
EXPOSE 3000
COPY  ./nginx/defalut.conf /etc/nginx/conf.d/default.conf
COPY --from=builder ./build  /usr/share/nginx/html