FROM ferrarimarco/github-changelog-generator:1.15.2

# Install bash.
RUN apk add --no-cache bash

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]