let string = 'madam';
let stack = [];
let reverseString = '';

for (i = 0; i < string.length; i++) {
  stack.push(string[i]);
}

for (i = 0; i < string.length; i++) {
  reverseString += stack.pop();
}

if (reverseString === string) {
  console.log('The given string is a palindrome');
} else {
  console.log('The given string is NOT a palindrome');
}
