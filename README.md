# Energy Management System

## Descriere generală


Acest proiect este un Sistem de Gestionare a Energiei care permite utilizatorilor (manageri și clienți) să monitorizeze și să gestioneze eficient consumul de energie. Sistemul include un frontend (Angular), un simulator și patru microservicii în Java Spring: unul pentru gestionarea utilizatorilor, unul pentru gestionarea dispozitivelor, unul pentru monitorizarea consumului, unul pentru chat

## Cerințe prealabile

Pentru a construi și rula acest proiect, ai nevoie de:

1) Docker 
2) JDK 21 pentru a compila backend-ul
3) Node.js 20 
4) MySQL pentru baza de date 


## Instrucțiuni de construire și rulare

Folosind Docker: 
- pentru imagini se execută:
1) docker build -t frontend .
2) docker build -t device-management .
3) docker build -t user-management .
4) docker build -t monitoring .
5) docker build -t chat .
- apoi: docker-compose up -d
Va fi nevoie de un utiliztor în baza de date. Într-un terminal se execută: 
1) docker exec -it user-db mysql -u root -p (se va solicita parola)
2) USE user 
3) INSERT INTO user(id_user, name, password, role) VALUES (UUID_TO_BIN('valoare UUID generata'), 'username', 'parola hash-10', 'ADMIN');


docker-compose down
docker-compose up -d


Local
- pentru Backend:
1) mvn clean install
2) mvn spring-boot:run
- pentru Frontend:
1) npm install
2) npm start


