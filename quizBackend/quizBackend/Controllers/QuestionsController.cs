using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using quizBackend.Models;
using Microsoft.EntityFrameworkCore;

namespace quizBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class QuestionsController : ControllerBase
    {
        readonly QuizContext context;

        public QuestionsController(QuizContext context)
        {
            this.context = context;
        }


        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<Question>> Get()
        {
            return context.Questions;

        }

        // POST api/questions
        [HttpPost]
        public async Task<IActionResult> Post([FromBody] Question question)
        {
            context.Questions.Add(question);
            Console.WriteLine(question.question);
            await context.SaveChangesAsync();
            return Ok(question);


        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Put(int id, [FromBody]Question question)
        {
            if(id != question.id)
            {
                return BadRequest();
            }


            context.Entry(question).State = EntityState.Modified;

            await context.SaveChangesAsync();

            return Ok(question);





        }






    }
}