import { CardContent, makeStyles } from "@material-ui/core";
import Skeleton from "@material-ui/lab/Skeleton";
import { FunctionComponent } from "react";

const useStyles = makeStyles({
  title: {
    margin: "0 auto 15px",
  },
  paragraph: {
    marginBottom: 15,
  },
});

export const LoadingSection: FunctionComponent<{}> = () => {
  const classes = useStyles();

  return (
    <CardContent>
      <Skeleton
        variant="circle"
        width={30}
        height={30}
        className={classes.title}
      />
      <Skeleton />
      <Skeleton />
      <Skeleton />
      <Skeleton width="60%" className={classes.paragraph} />

      <Skeleton />
      <Skeleton />
      <Skeleton />
      <Skeleton width="40%" />
    </CardContent>
  );
};
