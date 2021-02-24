export interface Story {
  id: number;
  name: string;
}

export interface Game {
  section: Section;
}

export interface Section {
  reference: number;
  text: string;
  actions: Action[];
}

export interface Action {
  id: number;
  description: string;
}
