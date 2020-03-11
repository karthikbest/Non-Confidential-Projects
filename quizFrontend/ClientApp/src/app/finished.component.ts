import { MAT_DIALOG_DATA } from "@angular/material";
import { Inject, Component } from "@angular/core";

@Component({
  template: `
    <h1>Your Score</h1>
    <h2>{{ data.correct }}/{{ data.total }}</h2>
  `
})
export class FinishedComponent {
  constructor(@Inject(MAT_DIALOG_DATA) private data) {}
}
