   M S B A M L   `   `   `  ����L  HRockPaperScissors, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null5clr-namespace:RockPaperScissorsRockPaperScissors  5   	   "    RockPaperScissors.MainWindow   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   1localclr-namespace:RockPaperScissors     5   	     � Title$+  %Rock Paper Scissors Lizard Spock Game��5   	   $	��550��67   $	��900��6D    � 
ResizeMode$ NoResize=�6P   
�������.��5	      �  � 	Resources 5
   
    �� 
TargetType �� Triggers :� RoutedEvent �� TargetProperty W� From	 W� To	 g� 	BeginTime
 g� Duration��%  '��       �� 5      " ��6    5      :� 5      $ Image.MouseLeftButtonDown��6#   .[�5      �� .��5      �� $ Width�6)   .��5   "   W� $	 100X�62   $	 200X�6=   $	 0:0:0X�6F   $
 0:0:0.8D�6X   5      5      5      5      5       � RowDefinitions 5   
   �� 5      $
��0.2* �6   6,   �� 5      $��* �6   6)   �� 5      $��* �6   6)    � ColumnDefinitions 5   
   �� 5      $��* �6   6+   �� 5      $��* �6   6+   �� 5      $��* �6   6+   �� 5       $��* �6   6+   �� 5!      $��* �6   6+   .+�5$   
   �� $��0��6   $��0��6   $��2��6-   $
��20,0q�6A   $��20(�6O   $��SemiBold"�6]   $��Center=�6s   $��Your Choice��6�   6�   �� 5%   
   $��0��6   $��3��6   $��2��6-   $
��20,0q�6A   $��20(�6O   $��SemiBold"�6]   $��Center=�6s   $��Opponent's Choice��6�   6�   �� 5'   
   -   5        $��imgPlayerChoice��5'   A   $��1��6   $��0��6   $��2��6-   $��20q�6X   $��Images/Blank.png��6d   ��6~   Z� �� 5(   
   -   5        $��imgCompChoice��5(   A   $��1��6   $��3��6   $��2��6-   $��20q�6V   $��Images/Blank.png��6b   ��6|   Z� �� 5*   
   -   5        $��
lblOutcome��5*   -   $��1��6   $��2��6   $
��0,50q�6@   $��24(�6N   $��Center=�6\   $��Center=�5+      ��6/   �� *EndingMessage+�� 5,   
   -   5        $��lblIntro��5,   E   $��1��6   $��1��6!   $��3��61   $
��80,0q�6V   $��14(�6d   $��Center=�6r   $��Center=�6�   $
��Wrap=�5-      $����The rules of this game are as follows: Scissors cuts paper, paper covers rock, rock crushes lizard, lizard poisons Spock, Spock smashes scissors, scissors decapitates lizard, lizard eats paper, paper disproves Spock, Spock vaporizes rock, and rock crushes scissors.

Please select a choice by clicking on one of the images below.��6!   �� 5/   
   -   5        $��imgRock��5/   -   $��2��6   $��0��6   
 � Tag$
 Rock��6<   $��20q�6G   $��Images/Rock.png��6S   6l   �� 50   
   -   5        $��imgPaper��50   -   $��2��6   $��1��6   $ Paper��6=   $��20q�6I   $��Images/Paper.png��6U   6o   �� 51   
   -   5        $��imgScissors��51   -   $��2��6   $��2��6   $ Scissors��6@   $��20q�6O   $��Images/Scissors.png��6[   6x   �� 52   
   -   5        $��	imgLizard��52   -   $��2��6   $��3��6   $ Lizard��6>   $��20q�6K   $��Images/Lizard.png��6W   6r   �� 53   
   -	   5        $��imgSpock��53   -   $��2��6   $��4��6   $ Spock��6=   $��20q�6I   $��Images/Spock.png��6U   6o   54      55      