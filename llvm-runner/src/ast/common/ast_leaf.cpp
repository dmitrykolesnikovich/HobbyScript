//
// Created by 刘丰恺 on 2018/8/2.
//

#include "ast_leaf.h"

ast_leaf::ast_leaf(const json &load_json) : ast_node(load_json) {
    this->token = load_json["token"];
}

const json &ast_leaf::get_token() {
    return this->token;
}

string ast_leaf::get_text() {
    string text = token["text"];
    return text;
}