using Microsoft.EntityFrameworkCore;
using quizBackend.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace quizBackend
{
    public class QuizContext : DbContext 
    {
        public QuizContext(DbContextOptions<QuizContext> options) : base(options) { }
        
       public DbSet<Question> Questions { get; set; }
        
       public DbSet<quizBackend.Models.Quiz> Quiz { get; set; }
        
    }
}
