using System;
using System.Collections.Generic;
using System.Text;

namespace EventGoData
{
   
    public class Event
    {
        public int Id { get; set;}
        public string Name { get; set; }
        public string Street { get; set; }
        public int houseNumber { get; set; }
        public string City { get; set; }
        public int PostalCode { get; set; }
        public string Venue { get; set; }
        public string Type { get; set;}
        public DateTime Date { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
        public string Description { get; set; }
        //public User Organisator { get; set; }
        public List<User> Attendees { get; set; }
        public byte[] CoverPhoto { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }

            public Event()
        {

        }
        public Event(int id , string name , string adress, int number , string city , int zip , string venue , string type , DateTime date , DateTime start , DateTime end , string description ,double latidute , double longitude, List<User> attendees = null,  byte[] img = null)
        {
            Type = type;
            Id = id;
            Name = name;
            Street = adress;
            houseNumber = number;
            City = city;
            PostalCode = zip;
            Venue = venue;
            Type = type;
            Date = date;
            StartTime = start;
            EndTime = end;
            Description = description;
          //  Organisator = organisator;
            CoverPhoto = img;
            Attendees = attendees;
            Longitude = longitude;
            Latitude = latidute;
        }
    }
}