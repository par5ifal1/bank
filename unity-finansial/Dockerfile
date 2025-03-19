FROM node:16-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build --prod

FROM nginx:alpine
COPY --from=build /app/dist/unity-finansial /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]




