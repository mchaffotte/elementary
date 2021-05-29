export type Story = {
  id: number;
  name: string;
};

export type Game = {
  section: Section;
};

export type Section = {
  storyId: number;
  reference: number;
  text: string;
  actions: Action[];
};

export type Action = {
  id: number;
  description: string;
};
