import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Typography,
} from "@material-ui/core";
import { FunctionComponent } from "react";
import { Link } from "react-router-dom";

import { Layout } from "../components/Layout";
import logo from "../assets/journey.png";

export const NotFound: FunctionComponent<{}> = () => {
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
            How is it possible?
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            Oops, you took the wrong direction, go back to the starting point!
          </Typography>
        </CardContent>
        <CardActions>
          <Button size="small" color="primary" component={Link} to="/">
            Home
          </Button>
        </CardActions>
      </Card>
    </Layout>
  );
};
