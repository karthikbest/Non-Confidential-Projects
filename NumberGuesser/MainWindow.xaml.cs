using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace NumberGuessingGame
{
    public partial class MainWindow : Window
    {
        private const string NUMBERS_ONLY_PATTERN = "[0-9]+";
        VM vm = new VM();
        public MainWindow()
        {
            InitializeComponent();
            DataContext = vm;
        }

        private void Play_Click(object sender, RoutedEventArgs e)
        {
            vm.Play();
        }

        private void TryButton_Click(object sender, RoutedEventArgs e)
        {
            vm.HandleAnswer();
        }

        private void Input_PreviewTextInput(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex(NUMBERS_ONLY_PATTERN);
            e.Handled = !regex.IsMatch(e.Text);
        }

        private void TextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                vm.HandleAnswer();
            }
        }
        private void Exit_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Window_MouseDown(object sender, MouseButtonEventArgs e)
        {
            if (e.ChangedButton == MouseButton.Left)
                this.DragMove();
        }
    }
}
