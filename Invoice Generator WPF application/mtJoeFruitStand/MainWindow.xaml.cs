using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
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
//My name is Karthik Appaswamy
namespace mtJoeFruitStand
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        vm vm = new vm();
        public MainWindow()
        {
            InitializeComponent();
            DataContext = vm;
        }

        private void BtnCalculate_Click(object sender, RoutedEventArgs e)
        {
            vm.calculateTotalAmount();
        }
    }
}
