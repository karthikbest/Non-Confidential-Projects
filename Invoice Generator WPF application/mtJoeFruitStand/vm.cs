using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
//My name is Karthik Appaswamy
namespace mtJoeFruitStand
{
    class vm : INotifyPropertyChanged
    {
        const decimal GRAPE_RATE = 8.49m;
        const decimal APPLE_RATE = 1.29m;
        const decimal ORANGE_RATE = 3.75m;
        const decimal TAX_RATE = 0.11m;
        const decimal DELIVERY_FEE_BELOW20 =12.5m;
        string file;

        public void calculateTotalAmount()
        {
            GrapeAmount = GrapeQty * GRAPE_RATE;
            AppleAmount = AppleQty * APPLE_RATE;
            OrangeAmount = OrangeQty * ORANGE_RATE;
            TaxAmount = (GrapeAmount + AppleAmount + OrangeAmount)*TAX_RATE;

            if ((GrapeAmount + AppleAmount + OrangeAmount)<20)
            {
                DeliveryCharge = DELIVERY_FEE_BELOW20;
            }
            
            TotalAmount = GrapeAmount + AppleAmount + OrangeAmount + TaxAmount + DeliveryCharge;

            //Creates a folder in Appdata for this app
            string path = System.IO.Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData), "JoeFruitStand");

            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);

            }
            file = System.IO.Path.Combine(path, "GeneratedInvoice.txt");
            File.AppendAllText(file, "JOE FRUIT STAND");
            if (AppleQty>0)
            File.AppendAllText(file,  "\n" + "APPLES - QUANTITY: " + AppleQty + "UNIT PRICE: " + APPLE_RATE + "TOTAL PRICE: " + AppleAmount );
            if (GrapeQty>0)
            File.AppendAllText(file, "\n" + "GRAPES - QUANTITY: " + GrapeQty + "UNIT PRICE: " + GRAPE_RATE + "TOTAL PRICE: " + GrapeAmount);
            if(OrangeQty>0)
            File.AppendAllText(file, "\n" + "ORANGES - QUANTITY: " + OrangeQty + "UNIT PRICE: " + ORANGE_RATE + "TOTAL PRICE: " + OrangeAmount);




        }

        private decimal privateAppleQty;
        public decimal AppleQty
        {
            get { return privateAppleQty; }
            set { privateAppleQty = value; onChange(); }
        }

        private decimal privateAppleAmount;
        public decimal AppleAmount
        {
            get { return privateAppleAmount; }
            set { privateAppleAmount = value; onChange(); }
        }

        private decimal privateOrangeQty;
        public decimal OrangeQty
        {
            get { return privateOrangeQty; }
            set { privateOrangeQty = value; onChange(); }
        }

        private decimal privateOrangeAmount;
        public decimal OrangeAmount
        {
            get { return privateOrangeAmount; }
            set { privateOrangeAmount = value; onChange(); }
        }


        private decimal privateGrapeQty;
        public decimal GrapeQty
        {
            get { return privateGrapeQty; }
            set { privateGrapeQty = value; onChange(); }
        }

        private decimal privateGrapeAmount;
        public decimal GrapeAmount
        {
            get { return privateGrapeAmount; }
            set { privateGrapeAmount = value; onChange(); }
        }
        private decimal privateTaxAmount;
        public decimal TaxAmount
        {
            get { return privateTaxAmount; }
            set { privateTaxAmount = value; onChange(); }
        }

        private decimal privateDeliveryCharge;
        public decimal DeliveryCharge
        {
            get { return privateDeliveryCharge; }
            set { privateDeliveryCharge = value; onChange(); }
        }

        private decimal privateTotalAmount;
        public decimal TotalAmount
        {
            get { return privateTotalAmount; }
            set { privateTotalAmount = value; onChange(); }
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
