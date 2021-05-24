import React from "react";
import { render } from "@testing-library/react";

import App from "./App";

test("renders Home page", () => {
  const { getByText } = render(<App />);

  const Stories = getByText("Choose the story");

  expect(Stories).toBeInTheDocument();
});
