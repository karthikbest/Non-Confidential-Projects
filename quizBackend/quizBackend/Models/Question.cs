using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace quizBackend.Models
{
    public class Question
    {
        public int id { get; set; }
        public string question { get; set; }
        public string correctAns { get; set; }
        public string wrongAns1 { get; set; }
        public string wrongAns2 { get; set; }
        public string wrongAns3 { get; set; }

        public int quizId { get; set; }

    }
}
