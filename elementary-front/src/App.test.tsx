import React from "react";
import { render } from "@testing-library/react";

import App from "./App";

test("renders Stories", () => {
  const { getByText } = render(<App />);

  const Stories = getByText("Stories");

  expect(Stories).toBeInTheDocument();
});
