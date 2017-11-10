using System;
using System.Collections.Generic;
using System.Text;

namespace EventGoData
{
   public class User
    {
        public int Id { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }
        public string Firstname { get; set; }
        public string Lastname { get; set; }
        public string Adress { get; set; }
        public string City { get; set; }
        public int PostalCode { get; set; }
       // public List<Event> OrganisedEvents { get; set; }
        public List<Event> Events { get; set; }
        public User()
        {

        }
        public User(int id,string username , string password , string email , string firstname , string lastname , string adres , int number , string city  , int zip)
        {
            Id = id;
            UserName = username;
            Password = password;
            Email = email;
            Firstname = firstname;
            Lastname = lastname;
            Adress = adres;
            City = city;
            PostalCode = zip;
           // OrganisedEvents = new List<Event>();
            Events = new List<Event>();
        }

    }
}
