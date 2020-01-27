using System;
using System.ComponentModel;
using System.IO;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using System.Windows;

namespace NumberGuessingGame
{
    class VM : INotifyPropertyChanged
    {
        const int LIMIT = 100;
        const int ATEMPTS = 7;
        private const string OUTPUT_DIRECTORY = "GuessNumber";
        private const string OUTPUT_FILE = "output.txt";
        private Visibility playButtonVisiblility = Visibility.Visible;
        private Visibility resetButtonVisiblility = Visibility.Hidden;
        private Visibility otherControlVisiblility = Visibility.Hidden;
        private Visibility finalVisiblility = Visibility.Hidden;
        private Visibility endVisibility = Visibility.Hidden;
        private string numLabelContent;
        private string textLabelContent;
        private readonly string OutputfileName = prepareOutputFile();
        private readonly Random r = new Random();
        private int guessedNumber;
        public int count;
        private string hint="Guess a number between 1 to 100.\n You have 7 chances";
        private string input;
        private string tryButtonText = "Start";
        public BindingList<string> Answers { get; set; } = new BindingList<string>();
        public string Input
        {
            get { return input; }
            set { input = value; onChange("Input"); }
        }
        
        public string TextLabelContent
        {
            get { return textLabelContent; }
            set { textLabelContent = value; onChange(); }
        }
        public string NumLabelContent
        {
            get { return numLabelContent; }
            set { numLabelContent = value; onChange(); }
        }
        public string Hint {
            get { return hint; }
            set { hint = value; onChange("Hint"); }
        }
        public string TryButtonText
        {
            get { return tryButtonText; }
            set { tryButtonText = value; onChange("TryButtonText"); }
        }

        public Visibility PlayButtonVisiblility
        {
            get { return playButtonVisiblility; }
            set { playButtonVisiblility = value; onChange(); }
        }
        public Visibility ResetButtonVisiblility
        {
            get { return resetButtonVisiblility; }
            set { resetButtonVisiblility = value; onChange(); }
        }

        public Visibility EndVisibility
        {
            get { return endVisibility; }
            set { endVisibility = value; onChange(); }
        }
        
        public Visibility OtherControlVisiblility
        {
            get { return otherControlVisiblility; }
            set { otherControlVisiblility = value; onChange(); }
        }

        public Visibility FinalVisiblility
        {
            get { return finalVisiblility; }
            set { finalVisiblility = value; onChange("FinalVisiblility"); }
        }

        public void Play()
        {
            PlayButtonVisiblility = Visibility.Hidden;
            OtherControlVisiblility = Visibility.Visible;
            ResetButtonVisiblility = Visibility.Visible;
            EndVisibility = Visibility.Hidden;
            FinalVisiblility = Visibility.Hidden;

            guessedNumber = r.Next(1, LIMIT + 1);
            Answers.Clear();
            count = 1;
            File.AppendAllText(OutputfileName, $"\r\n{DateTime.Now.ToString()}: Guessed number: {guessedNumber} Answers:");
            Input = string.Empty;
            Hint = "Guess a number between 1 to 100.\n You have 7 chances";
        }

        public void Replay()
        {
            Play();
        }

        private static string prepareOutputFile()
        {
            string path = System.IO.Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData), OUTPUT_DIRECTORY);
            if (!Directory.Exists(path))
                Directory.CreateDirectory(path);
            return System.IO.Path.Combine(path, OUTPUT_FILE);
        }

        public async void HandleAnswer()
        {
            int answeredNumber;
            if (!int.TryParse(Input, out answeredNumber))
            {
                return;
            }
            if (answeredNumber == guessedNumber)
            {
                winGame();
                return;
            }
            else if (answeredNumber > guessedNumber)
            {
                Answers.Add($"{Input} Too high!");
            }
            else if (answeredNumber < guessedNumber)
            {
                Answers.Add($"{Input} Too low!");
                showHints(answeredNumber);
            }
            saveAnswer();
            showHints(answeredNumber);
            Input = string.Empty;
            count++;
            if (ATEMPTS < count)
            {
                loseGame(guessedNumber);
                return;
            }
        }

        private void showHints(int attemptNumber)
        {
            if (attemptNumber > LIMIT)
            {
                Hint = "Be attentive!\r\nYou are entring number out of current range";
                return;
            }
        }
        
        private async void winGame()
        {
            OtherControlVisiblility = Visibility.Hidden;
            FinalVisiblility = Visibility.Visible;
            EndVisibility = Visibility.Hidden;
            await Task.Delay(5000);
            FinalVisiblility = Visibility.Hidden;
            ResetButtonVisiblility = Visibility.Visible;
        }

        private async void loseGame(int guessedNumber)
        {
            string message = $"Oops!\n Better Luck next time!!!\r\n";
            Hint = message;
            await Task.Delay(2000);
            OtherControlVisiblility = Visibility.Hidden;
            EndVisibility = Visibility.Visible;
            NumLabelContent = guessedNumber.ToString();
            TextLabelContent = " Is The\nNumber";
            ResetButtonVisiblility = Visibility.Visible;
        }

        private void saveAnswer()
        {
            File.AppendAllText(OutputfileName, " " + Input);
        }

        #region PropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;

        private void onChange([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        #endregion
    }
}
