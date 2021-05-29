import { render } from "@testing-library/react";

import App from "./App";

test("renders Home page", () => {
  const { getByText } = render(<App />);

  const newGame = getByText("New Game");

  expect(newGame).toBeInTheDocument();
});
