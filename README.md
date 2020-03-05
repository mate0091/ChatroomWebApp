# Realtime Chatroom application

## Basic description
This app is similar to many of the contemporary chat applications and its mechanisms. The scope of the app is to let two or more users send text messages between eachother. Users can send direct messages to eachother or can enter a "room" where 2 or more people chat at once and everyone receives everyones messages.

## Data persistency
Every message sent will be stored in a database. Messages could be deleted or edited if necessary.
## User types
### Administrator
Performs CRUD operations on the users database, can delete and edit any message, can assign moderators to rooms, can do all the things the rest can

### Moderator 
Can ban users from rooms, can remove certain messages from the room they are moderating + can do anything a user can. Can approve a users' request to join a room.

### User
Can send or receive messages, can request to join a room, can delete or edit only their messages.
Every user has a friends list, where they can add or remove users based on their username.

## To be thought about
Add multiple types of messages to be rendered differently.(location, gifs, pictures, text).

## Details
Application will be on the web.
