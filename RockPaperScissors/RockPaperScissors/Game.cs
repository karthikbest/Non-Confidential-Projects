using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace RockPaperScissors
{
    class Game : INotifyPropertyChanged
    {
        const int CHOICE_AMOUNT = 5;
        const int FIRST_OFFSET = 1;
        const int SECOND_OFFSET1 = 2;
        const int SECOND_OFFSET2 = 1;
        public enum Choices { ROCK = 1, PAPER = 2, SCISSORS = 3, SPOCK = 4, LIZARD = 5 };
        public enum Outcome { COMPWIN, PLAYERWIN, TIE };
        readonly Random r = new Random();

        private Choices compChoice;
        public Choices CompChoice
        {
            get { return compChoice; }
            set { compChoice = value; NotifyChange(); }
        }
        private Choices playerChoice;
        public Choices PlayerChoice
        {
            get { return playerChoice; }
            set { playerChoice = value; NotifyChange(); }
        }
        private string endingMessage;
        public string EndingMessage
        {
            get { return endingMessage; }
            set { endingMessage = value; NotifyChange(); }
        }

        #region PropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyChange([CallerMemberName] string property = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(property));
        }
        #endregion

        public void PlayGame(string pchoice)
        {
            PickCompChoice();
            switch (pchoice)
            {
                case "Rock":
                    playerChoice = Choices.ROCK;
                    break;
                case "Paper":
                    playerChoice = Choices.PAPER;
                    break;
                case "Scissors":
                    playerChoice = Choices.SCISSORS;
                    break;
                case "Spock":
                    playerChoice = Choices.SPOCK;
                    break;
                case "Lizard":
                    playerChoice = Choices.LIZARD;
                    break;
            }
            switch (GetWinner())
            {
                case Outcome.PLAYERWIN:
                    EndingMessage = "You win!";
                    break;
                case Outcome.COMPWIN:
                    EndingMessage = "You lose.";
                    break;
                case Outcome.TIE:
                    EndingMessage = "It's a tie.\nPlay again.";
                    break;
            }
        }

        private void PickCompChoice()
        {
            switch (r.Next(CHOICE_AMOUNT))
            {
                case 0:
                    CompChoice = Choices.ROCK;
                    break;
                case 1:
                    CompChoice = Choices.PAPER;
                    break;
                case 2:
                    CompChoice = Choices.SCISSORS;
                    break;
                case 3:
                    CompChoice = Choices.SPOCK;
                    break;
                case 4:
                    CompChoice = Choices.LIZARD;
                    break;
            }
        }

        private Outcome GetWinner()
        {
            Outcome outcome;
            if ((int)compChoice % CHOICE_AMOUNT + FIRST_OFFSET == (int)playerChoice || ((int)compChoice + SECOND_OFFSET1) % CHOICE_AMOUNT + SECOND_OFFSET2 == (int)playerChoice)
                outcome = Outcome.PLAYERWIN;
            else if ((int)playerChoice % CHOICE_AMOUNT + FIRST_OFFSET == (int)compChoice || ((int)playerChoice + SECOND_OFFSET1) % CHOICE_AMOUNT + SECOND_OFFSET2 == (int)compChoice)
                outcome = Outcome.COMPWIN;
            else
                outcome = Outcome.TIE;
            return outcome;
        }
    }
}
