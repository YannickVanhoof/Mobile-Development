using EventGoData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Results;



namespace EventGoApi.Controllers
{
    public class EventController : ApiController
    {
        private Repository repository;
        public EventController()
        {
            repository = new Repository();
        }
        // GET: api/Event
        public IHttpActionResult Get()
        {
           return Ok(repository.GetAllEvents());
        }

        // GET: api/Event/5
        public IHttpActionResult Get(int id)
        {
            return Ok(repository.GetEventsById(id));
        }
        [Route ("api/event/attendees/{id}")]
        public IHttpActionResult GetAttendeesByEvent(int id)
        {
         return Ok(repository.GetAllAttendeesForEvents(id));

        }

        // POST: api/Event
        public IHttpActionResult Post(Event e)
        {
            if (ModelState.IsValid)
            {
                repository.addEvent(e);
                return Ok();
            }
            return BadRequest();
        }

        // PUT: api/Event/5
        public IHttpActionResult Put(int id, Event e)
        {
            if (ModelState.IsValid)
            {
                Event ev = repository.UpdateEvent(id, e);
                return Ok(ev);
            }
            return BadRequest();
            
        }
        [Route("api/event/{id}/addUser")]
        public IHttpActionResult AddUserToEvent(int id , User user)
        {
            if (ModelState.IsValid)
            {
                repository.AddUserToAttendees(user, id);
                return Ok();
            }
            return BadRequest();
        }

        // DELETE: api/Event/5
        public void Delete(int id)
        {
            repository.deleteEvent(id);
        }
    }
}
