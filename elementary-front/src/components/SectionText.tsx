import { CardMedia, Typography, makeStyles } from "@material-ui/core";
import { FunctionComponent } from "react";
import ReactMarkdown from "react-markdown";

const useStyles = makeStyles({
  media: {
    height: 500,
  },
});

type SectionTextProps = {
  text: string;
  storyId: number;
};

const SectionText: FunctionComponent<SectionTextProps> = ({
  text,
  storyId,
}) => {
  const classes = useStyles();

  return (
    <ReactMarkdown
      components={{
        img: ({ src, ...props }) => (
          <CardMedia
            className={classes.media}
            image={`http://localhost:8181/stories/${storyId}/images/${src}`}
            {...props}
          />
        ),
        p: ({ children }) => {
          return (
            <>
              {children
                .filter((child) => typeof child === "string" && child !== "\n")
                .map((child, index) => {
                  return (
                    <Typography key={index} align="justify" paragraph>
                      {child}
                    </Typography>
                  );
                })}
            </>
          );
        },
      }}
    >
      {text}
    </ReactMarkdown>
  );
};

export default SectionText;
