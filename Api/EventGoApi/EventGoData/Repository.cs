﻿using System;
using System.Collections.Generic;
using System.Text;

namespace EventGoData
{
    public class Repository
    {
        private static int eventId = 6;
        private static int userId = 6;
        private  static List<Event> events = new List<Event>()
        {
            new Event(1,"De nieuwjaarsfuif",
                "Pater van Henxthovenstraat" ,
                1,
                "Mol",
                2400,
                "KSA MOL" ,
                "Party" ,
                 new DateTime(2017 , 12 , 31) ,
                 new DateTime(2017,12,31 , 22 , 0 , 0),
                 new DateTime(2018 , 01 , 01 , 6 , 0 , 0),
                 "De nieuwjaars van KSA mol is terug." ,
                 51.186599,
                 5.102355
                 ) ,

                new Event(2,"Kendrick lamar",
                "Schijnpoortweg " ,
                119,
                "Antwerpen",
                2170,
                "Sportpaleis" ,
                "Music" ,
                 new DateTime(2018 , 2 , 26) ,
                 new DateTime(2018,2,26 , 20 , 0 , 0),
                 new DateTime(2018 , 2 , 26 , 23 , 0 , 0),
                "Top Dawg Entertainment labelartiest en zevenvoudig Grammywinnaar Kendrick Lamar kondigt vandaag de Europese data aan van ‘THE DAMN. TOUR’: 15 data in 11 verschillende landen! De tour start begin februari in Dublin en brengt de rapper op dinsdag 27 februari naar het Sportpaleis. Niemand minder dan Brits electropopper James Blake neemt de support voor zijn rekening." ,
                51.230194,
                4.4429298
                )
                ,
                 new Event(3,"Euro Cycling XP",
                "Gouverneur Verwilghensingel" ,
                70,
                "Hasselt",
                3500,
                "Ethias Arena" ,
                "Expo" ,
                 new DateTime(2017 , 10 , 12) ,
                 new DateTime(2017,10,22 , 18 , 0 , 0),
                 new DateTime(2018 , 01 , 01 , 6 , 0 , 0),
                 "MECC Maastricht presenteert van 20 t/m 22 oktober Euro Cycling XP als dé afsluiter van het wieler- triatlon en MTB seizoen en start van het winterseizoen veldrijden en ATB. Het is de plek waar de fietser wordt geïnspireerd, de community elkaar ontmoet en kennis en ervaringen worden gedeeld.",
                 50.9343811,
                 5.3620677
                )
                 ,
                  new Event(4, "Belgisch kampioenschap tapdans",
                "Gouverneur Verwilghensingel" ,
                71,
                "Hasselt",
                3500,
                "Versus" ,
                "Dance" ,
                 new DateTime(2017 , 10 , 22) ,
                 new DateTime(2017,10, 22 , 12 , 0 , 0),
                 new DateTime(2018 , 10 , 22 , 20 , 0 , 0),
                 "Het is weer tijd voor het wereldkampioenschap tapdans" ,
                 50.932684,
                 5.364077
                ) ,
               new Event(5,"KRC Genk - Stvv" ,
               "Stadionplein",
                1,
                "Genk",
                3600,
                "Luminus arena" ,
                "Sport" ,
                 new DateTime(2018 , 1 , 26) ,
                 new DateTime(2018,1, 26 , 20 , 0 , 0),
                 new DateTime(2018 , 1 , 26 , 23 , 0 , 0),
                 "De limburgse derby" ,
                 51.0050212,
                 5.5334055
                ) ,
             };
        private static List<User> users = new List<User>()
            {
                new User(1,
                        "YannickVh" ,
                        "wachtwoord",
                        "yannick.vanhoof@student.pxl.be",
                        "Yannick",
                        "Vanhoof",
                        "Brandstraat" ,
                        47 ,
                        "Mol",
                        2400),
                    new User(2,"KimP" ,
                        "wachtwoord",
                        "kim.przybylski@student.pxl.be",
                        "Kim",
                        "przybylski",
                        "Bokrijkse weg" ,
                        8 ,
                        "Zonhoven",
                        3520),
                    new User(3,"Sportpaleis" ,
                        "wachtwoord",
                        "sport.paleis@hotmail.com",
                        "John",
                        "Doe",
                        "De meir" ,
                        8 ,
                        "Antwerpen",
                        2170),
                    new User(4,"JosefienV" ,
                        "wachtwoord",
                        "josefien.verthongen@hotmail.com",
                        "Josefien",
                        "Verthongen",
                        "Kempische steenweg" ,
                        72 ,
                        "Hasselt",
                        3500),
                     new User(5,"PeterC" ,
                        "wachtwoord",
                        "Peter.Croonen@krcgenk.be",
                        "Peter",
                        "Croonen",
                        "Weg naar As" ,
                        81 ,
                        "Genk",
                        3600),


            };
       
        public Repository()
        {
                        
           
          
        }

        public Event UpdateEvent(int id, Event e)
        {
            if (e.Id == id)
            {
                Event ev = events.Find(even => even.Id == id);
                events.Remove(ev);
                events.Add(ev);
                return ev;
            }
            else
            {
                return null;
            }
           
            

        }

        public User UpdateUser(int id, User u)
        {
            if (u.Id == id)
            {
                User user = users.Find(us => us.Id == id);
                users.Remove(user);
                users.Add(u);
                return user;
            }
            else
            {
                return null;
            }
        }

        public void deleteEvent(int id)
        {
          events.Remove(events.Find(e => e.Id == id));
        }
        public void deleteUser(int id)
        {
            users.Remove(users.Find(u => u.Id == id));
        }
        public List<User> GetAllUsers()
        {
            return users;
        }
        public User GetUserById(int id)
        {
            return users.Find(u => u.Id == id);
        }
        
        public List<Event> GetAllEvents()
        {
            return events;
        }
        public Event GetEventsById(int id)
        {
            return events.Find(e => e.Id == id);
        }
        public List<User> GetAllAttendeesForEvents(int id)
        {
            return events.Find(e => e.Id == id).Attendees;
        }

        
        public List<Event> getMyEvents(int id)
        {
            List<Event> eventList = new List<Event>();
          
            User u = users.Find(user => user.Id == id);
            var iets = u.UserName;
            var ietsl = u.Events;
           foreach (Event e in events)
            {
                var eventName = e.Name;
                if (e.Attendees == null)
                {
                    e.Attendees = new List<User>();
                }
                foreach(User user in e.Attendees)
                {
                    var temp = user.UserName;
                    var tempL = user.Events;
                    if (user.UserName == u.UserName)
                    {
                        eventList.Add(e);   }
                }
            }
            return eventList;
        }
        public List<Event> addEvent(Event e)
        {
            e.Id = eventId;
            events.Add(e);
            eventId++;
            return events;
        }
        public List<User> addUser(User u)
        {
            u.Id = userId;
            users.Add(u);
            userId++;
            return users;
        }
        public void AddUserToAttendees(User user , int id)
        {
            if(events.Find(e => e.Id == id).Attendees == null)
            {
                events.Find(e => e.Id == id).Attendees = new List<User>();
            }
            events.Find(e => e.Id == id).Attendees.Add(user);
            users.Find(u => u.Id == id).Events.Add(events.Find(e => e.Id == id));

        }
        public User GetUserByName(string username)
        {
            return users.Find(u => u.UserName == username);
        }

    }
}

