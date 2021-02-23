module.exports = {
  content: ["src/**/*.cljs"],
  css: ["public/css/style.css"],
  defaultExtractor: (content) => content.match(/[\w-/.:]+(?<!:)/g) || [],
};