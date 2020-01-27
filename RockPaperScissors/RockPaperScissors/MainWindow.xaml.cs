using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media.Imaging;

namespace RockPaperScissors
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        Game game;
        const string IMG_ROCK_PATH = @"/Images/Rock.png";
        const string IMG_PAPER_PATH = @"/Images/Paper.png";
        const string IMG_SCISSORS_PATH = @"/Images/Scissors.png";
        const string IMG_SPOCK_PATH = @"/Images/Spock.png";
        const string IMG_LIZARD_PATH = @"/Images/Lizard.png";
        public MainWindow()
        {
            InitializeComponent();
            game = new Game();
            DataContext = game;
        }
        public void MakeChoice(object sender, RoutedEventArgs e)
        {
            lblIntro.Visibility = Visibility.Hidden;
            Image image = sender as Image;
            game.PlayGame(image.Tag.ToString());
            switch (game.PlayerChoice)
            {
                case Game.Choices.ROCK:
                    imgPlayerChoice.Source = new BitmapImage(new Uri(IMG_ROCK_PATH, UriKind.Relative));
                    break;
                case Game.Choices.PAPER:
                    imgPlayerChoice.Source = new BitmapImage(new Uri(IMG_PAPER_PATH, UriKind.Relative));
                    break;
                case Game.Choices.SCISSORS:
                    imgPlayerChoice.Source = new BitmapImage(new Uri(IMG_SCISSORS_PATH, UriKind.Relative));
                    break;
                case Game.Choices.SPOCK:
                    imgPlayerChoice.Source = new BitmapImage(new Uri(IMG_SPOCK_PATH, UriKind.Relative));
                    break;
                case Game.Choices.LIZARD:
                    imgPlayerChoice.Source = new BitmapImage(new Uri(IMG_LIZARD_PATH, UriKind.Relative));
                    break;
            }
            switch (game.CompChoice)
            {
                case Game.Choices.ROCK:
                    imgCompChoice.Source = new BitmapImage(new Uri(IMG_ROCK_PATH, UriKind.Relative));
                    break;
                case Game.Choices.PAPER:
                    imgCompChoice.Source = new BitmapImage(new Uri(IMG_PAPER_PATH, UriKind.Relative));
                    break;
                case Game.Choices.SCISSORS:
                    imgCompChoice.Source = new BitmapImage(new Uri(IMG_SCISSORS_PATH, UriKind.Relative));
                    break;
                case Game.Choices.SPOCK:
                    imgCompChoice.Source = new BitmapImage(new Uri(IMG_SPOCK_PATH, UriKind.Relative));
                    break;
                case Game.Choices.LIZARD:
                    imgCompChoice.Source = new BitmapImage(new Uri(IMG_LIZARD_PATH, UriKind.Relative));
                    break;
            }
        }
    }
}
