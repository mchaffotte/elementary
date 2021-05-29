import { useQuery, gql } from "@apollo/client";
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

const GET_GAME = gql`
  query getGame {
    game {
      section {
        storyId
      }
    }
  }
`;

export const Home: FunctionComponent<{}> = () => {
  const { loading, error } = useQuery(GET_GAME);

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
          <Button
            color="primary"
            component={Link}
            to="/game"
            disabled={loading || !!error}
          >
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
