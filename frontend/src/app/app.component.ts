import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Amazon-clone';

  items = [{ img: 'https://picsum.photos/200/200?random=1' },
    { img: 'https://picsum.photos/200/200?random=2' },
    { img: 'https://picsum.photos/200/200?random=3' },
    { img: 'https://picsum.photos/200/200?random=4' },
    { img: 'https://picsum.photos/200/200?random=5' },
    { img: 'https://picsum.photos/200/200?random=6' },
    { img: 'https://picsum.photos/200/200?random=7' },
    { img: 'https://picsum.photos/200/200?random=8' }
  ];

}
