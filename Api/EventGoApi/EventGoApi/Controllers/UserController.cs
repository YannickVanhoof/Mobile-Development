using EventGoData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace EventGoApi.Controllers
{
    public class UserController : ApiController
    {
        private Repository repository;
        public UserController()
        {
            repository = new Repository();
        }
        // GET: api/User
        public IHttpActionResult Get()
        {
            return Ok(repository.GetAllUsers());
        }
        [Route("api/User/Name/{userName}")]
        public IHttpActionResult Get(string userName)
        {
            return Ok(repository.GetUserByName(userName));
        }

        // GET: api/User/5
        public IHttpActionResult Get(int id)
        {
            return Ok(repository.GetUserById(id));
        }
        [Route ("api/User/{id}/Events")]
        public IHttpActionResult getMyEvents(int id)
        {
            return Ok(repository.getMyEvents(id));
        }
        // POST: api/User
        public IHttpActionResult Post(User value)
        {
            if (ModelState.IsValid)
            {
                repository.addUser(value);
                return Ok();
            }
            return BadRequest();
           
        }

        // PUT: api/User/5
        public IHttpActionResult Put(int id, User value)
        {
            if (ModelState.IsValid)
            {
                repository.UpdateUser(id , value);
                return Ok();
            }
            return BadRequest();
        }

        // DELETE: api/User/5
        public void Delete(int id)
        {
            repository.deleteUser(id);
        }
    }
}
