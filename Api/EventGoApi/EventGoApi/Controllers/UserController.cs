﻿using EventGoData;
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

        // GET: api/User/5
        public IHttpActionResult Get(int id)
        {
            return Ok(repository.GetUserById(id));
        }
        [Route ("api/User/Events")]
        public IHttpActionResult getMyEvents()
        {
            return Ok(repository.getMyEvents(2));
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
