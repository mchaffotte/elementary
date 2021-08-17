export type Story = {
  id: number;
  name: string;
};

export type Game = {
  character: Character;
  section: Section;
};

export type Character = {
  name: string;
  money: Money;
  skills: Skill[];
};

export type Money = {
  pounds: number;
  shillings: number;
  pence: number;
};

export type Skill = {
  name: string;
  value: string;
};

export type Section = {
  storyId: number;
  reference: number;
  text: string;
  actions: Action[];
};

export type Action = {
  description: string;
  answerNeeded: boolean;
};
