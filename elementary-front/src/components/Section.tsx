import { FunctionComponent } from "react";
import ReactMarkdown from "react-markdown";

import { Section } from "../api";

const getRenderers = (storyId: number) => {
  return {
    image: ({
      alt,
      src,
      title,
    }: {
      alt?: string;
      src?: string;
      title?: string;
    }) => {
      return (
        <img
          alt={alt}
          src={`http://localhost:8181/stories/${storyId}/images/${src}`}
          title={title}
        />
      );
    },
  };
};

type SectionPageProps = {
  section: Section | null;
  storyId: number;
  onTurnTo: Function;
};

const SectionPage: FunctionComponent<SectionPageProps> = ({
  section,
  storyId,
  onTurnTo,
}) => {
  if (!section) {
    return null;
  }
  return (
    <div>
      <span>{section.reference}</span>
      <ReactMarkdown source={section.text} renderers={getRenderers(storyId)} />
      <div>
        {section.actions.map((action, index) => (
          <div>
            {action.description}
            <button onClick={() => onTurnTo(index)}>Next</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SectionPage;
