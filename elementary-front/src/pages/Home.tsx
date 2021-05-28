import {
  Button,
  Card,
  CardContent,
  CardMedia,
  CardActions,
  Typography,
} from "@material-ui/core";
import { FunctionComponent } from "react";
import { Link } from "react-router-dom";

import { Layout } from "../components/Layout";
import logo from "../assets/journey.png";

export const Home: FunctionComponent<{}> = () => {
  return (
    <Layout>
      <Card variant="outlined">
        <CardMedia
          component="img"
          alt="Wrong Direction"
          height="140"
          image={logo}
          title="Wrong Direction"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            Elementary
          </Typography>
        </CardContent>
        <CardActions>
          <Button color="primary" component={Link} to="/game">
            Continue
          </Button>
          <Button color="primary" component={Link} to="/new-game">
            New Game
          </Button>
        </CardActions>
      </Card>
    </Layout>
  );
};
