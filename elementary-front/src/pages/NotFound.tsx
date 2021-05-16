import { FunctionComponent } from "react";
import { Link } from "react-router-dom";

export const NotFound: FunctionComponent<{}> = () => {
  return (
    <>
      <Link to={"/"}>Home</Link>
      <h2>Page not found</h2>
    </>
  );
};
